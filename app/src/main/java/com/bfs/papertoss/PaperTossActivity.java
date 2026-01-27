package com.bfs.papertoss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.backflipstudios.android.debug.BFSDebug;
import com.backflipstudios.android.engine.app.BFSActivityAddon;
import com.backflipstudios.android.engine.app.addons.BFSBannerAdActivityAddon;
import com.backflipstudios.android.engine.app.addons.BFSInterstitialAdActivityAddon;
import com.backflipstudios.android.engine.platform.BFSDeviceInfo;
import com.backflipstudios.android.googleanalytics.BFSGoogleAnalyticsActivityAddon;
import com.backflipstudios.android.mopub.BFSMoPubBannerAdAddon;
import com.backflipstudios.android.mopub.BFSMoPubConversionTrackerAddon;
import com.backflipstudios.android.mopub.BFSMoPubInterstitialAdAddon;
import com.bfs.papertoss.cpp.Papertoss;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Globals;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class PaperTossActivity extends Activity implements BFSInterstitialAdActivityAddon.ActivityListener {
    private static final boolean ADS_ENABLED = true;
    private static final long STARTUP_INTERSTITIAL_PAUSE_INTERVAL = 300000;
    private ExitPressed exitPressed;
    GoToMenu gotoMenu;
    HideAds hideAds;
    ShowGameplayAds showGameplayAds;
    private PapertossGLSurfaceView m_glView = null;
    private ImageView m_splashImage = null;
    private RelativeLayout m_layout = null;
    private boolean m_startedGame = false;
    private boolean m_adWillShow = false;
    private boolean m_adDidShow = false;
    private long m_pauseTime = 0;
    private HashMap<String, BFSActivityAddon> m_addons = null;
    private String m_ad_device_type = "phone";
    private String m_ad_storefront_type = "google";

    public PaperTossActivity() {
        this.exitPressed = new ExitPressed();
        this.showGameplayAds = new ShowGameplayAds();
        this.gotoMenu = new GoToMenu();
        this.hideAds = new HideAds();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        BFSGoogleAnalyticsActivityAddon gaaa;
        BFSDebug.i("PaperTossActivity.onCreate");
        super.onCreate(savedInstanceState);
        Globals.m_activity = this;
        buildUserInterface();
        this.m_startedGame = false;
        this.m_adWillShow = false;
        this.m_adDidShow = false;
        this.m_pauseTime = 0L;
        this.m_addons = new HashMap<>();
        BFSMoPubConversionTrackerAddon mpcta = new BFSMoPubConversionTrackerAddon(this);
        mpcta.onCreate();
        this.m_addons.put(mpcta.getName(), mpcta);
        if (BFSDeviceInfo.isTabletDevice(this)) {
            gaaa = new BFSGoogleAnalyticsActivityAddon(this, PaperTossThirdParty.GOOGLE_ANALYTICS_TABLET_KEY);
        } else {
            gaaa = new BFSGoogleAnalyticsActivityAddon(this, PaperTossThirdParty.GOOGLE_ANALYTICS_PHONE_KEY);
        }
        gaaa.onCreate();
        this.m_addons.put(gaaa.getName(), gaaa);
        BFSMoPubInterstitialAdAddon interstitials = new BFSMoPubInterstitialAdAddon(this, true);
        interstitials.onCreate();
        this.m_addons.put(interstitials.getName(), interstitials);
        if (BFSDeviceInfo.isTabletDevice(this)) {
            this.m_ad_device_type = "tablet";
        }
        if (PaperTossApplication.getInstance().checkBuildConfigurationParameter("target-storefront", "amazon")) {
            this.m_ad_storefront_type = "amazon";
        }
        interstitials.registerAd("startup_interstitial", PaperTossThirdParty.query("MOPUB", this.m_ad_storefront_type, this.m_ad_device_type, "STARTUP_INTERSTITIAL"), BFSInterstitialAdActivityAddon.InterstitialType.StartupInterstitial);
        interstitials.prepareAdWithTag("startup_interstitial");
    }

    protected void buildUserInterface() {
        this.m_layout = new RelativeLayout(this);
        this.m_layout.setBackgroundColor(-16777216);
        this.m_splashImage = new ImageView(this);
        this.m_splashImage.setImageResource(R.drawable.splash);
        this.m_splashImage.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams p0 = new RelativeLayout.LayoutParams(-1, -1);
        this.m_layout.addView(this.m_splashImage, p0);
        setContentView(this.m_layout);
    }

    @Override // android.app.Activity
    public void onStart() {
        BFSDebug.i("PaperTossActivity.onStart");
        super.onStart();
        PaperTossApplication.getInstance().startFlurrySession();
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            addon.onStart();
            if (addon.getType() == BFSActivityAddon.AddonType.InterstitialAds) {
                ((BFSInterstitialAdActivityAddon) addon).addActivityListener(this);
            }
        }
    }

    @Override // android.app.Activity
    public void onResume() throws Throwable {
        BFSDebug.i("PaperTossActivity.onResume");
        super.onResume();
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            addon.onResume();
        }
        boolean resumingFromAd = this.m_adWillShow | this.m_adDidShow;
        this.m_adWillShow = false;
        this.m_adDidShow = false;
        int runCount = PaperTossApplication.getInstance().getRunCount();
        if (!this.m_startedGame) {
            if (!resumingFromAd) {
                if (!showStartupInterstitialAd(runCount)) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.1
                        @Override // java.lang.Runnable
                        public void run() throws Throwable {
                            PaperTossActivity.this.startGame();
                        }
                    }, 2000L);
                    return;
                }
                return;
            }
            startGame();
            return;
        }
        boolean adShown = false;
        if (!resumingFromAd) {
            long millisSince = Math.abs(System.currentTimeMillis() - this.m_pauseTime);
            if (millisSince >= STARTUP_INTERSTITIAL_PAUSE_INTERVAL) {
                adShown = showStartupInterstitialAd(runCount);
            }
        }
        if (!adShown) {
            if (this.m_glView != null) {
                this.m_glView.onResume();
            }
            if (Globals.soundMgr != null) {
                Globals.soundMgr.startBackgroundSound();
            }
        }
    }

    private boolean showStartupInterstitialAd(int runCount) {
        if (!BFSDeviceInfo.isConnectedToNetwork(this) || runCount < 3) {
            return false;
        }
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            if (addon.getType() == BFSActivityAddon.AddonType.InterstitialAds) {
                ((BFSInterstitialAdActivityAddon) addon).showAdWithTag("startup_interstitial");
                return true;
            }
        }
        return false;
    }

    private boolean showGameplayInterstitialAd() {
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            if (addon.getType() == BFSActivityAddon.AddonType.InterstitialAds && ((BFSInterstitialAdActivityAddon) addon).isAdWithTagPrecached("gameplay_interstitial")) {
                ((BFSInterstitialAdActivityAddon) addon).showAdWithTag("gameplay_interstitial");
                return true;
            }
        }
        return false;
    }

    @Override // android.app.Activity
    public void onPause() {
        BFSDebug.i("PaperTossActivity.onPause");
        super.onPause();
        this.m_pauseTime = System.currentTimeMillis();
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            addon.onPause();
        }
        if (this.m_glView != null) {
            this.m_glView.onPause();
            this.m_glView.queueEvent(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Papertoss.deactiviate();
                        Papertoss.shutdown();
                    } catch (Exception e) {
                        PaperTossApplication.logErrorWithFlurry("onPause", e, "PaperToss");
                    }
                }
            });
            if (Globals.soundMgr != null) {
                Globals.soundMgr.stopBackgroundSound();
            }
        }
    }

    @Override // android.app.Activity
    public void onStop() {
        BFSDebug.i("PaperTossActivity.onStop");
        super.onStop();
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            if (addon.getType() == BFSActivityAddon.AddonType.InterstitialAds) {
                ((BFSInterstitialAdActivityAddon) addon).removeActivityListener(this);
            }
            addon.onStop();
        }
        PaperTossApplication.getInstance().closeFlurrySession();
    }

    @Override // android.app.Activity
    public void onDestroy() throws Throwable {
        BFSDebug.i("PaperTossActivity.onDestroy");
        super.onDestroy();
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            addon.onDestroy();
        }
        this.m_addons.clear();
        handleEventSubscriptions(false);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Set<String> keys = this.m_addons.keySet();
        for (String key : keys) {
            BFSActivityAddon addon = this.m_addons.get(key);
            addon.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleEventSubscriptions(boolean subscribe) throws Throwable {
        Evt evt = Evt.getInstance();
        if (subscribe) {
            evt.subscribe("gotoMenu", this.gotoMenu);
            evt.subscribe("gotoLevel", this.showGameplayAds);
            if (!Globals.HI_RES) {
                evt.subscribe("gotoScores", this.hideAds);
            }
            evt.subscribe("onExitPressed", this.exitPressed);
            return;
        }
        evt.unsubscribe("gotoMenu", this.gotoMenu);
        evt.unsubscribe("gotoLevel", this.showGameplayAds);
        evt.unsubscribe("gotoScores", this.hideAds);
        evt.unsubscribe("onExitPressed", this.exitPressed);
    }

    private void startGame() throws Throwable {
        if (!this.m_startedGame) {
            BFSDebug.i("PaperTossActivity.startingGame()");
            this.m_startedGame = true;
            BFSMoPubBannerAdAddon banners = new BFSMoPubBannerAdAddon(this, this.m_layout, false);
            banners.onCreate();
            this.m_addons.put(banners.getName(), banners);
            banners.registerAd("main_menu", PaperTossThirdParty.query("MOPUB", this.m_ad_storefront_type, this.m_ad_device_type, "MENU_BANNER"), BFSBannerAdActivityAddon.BannerPosition.BannerPositionBottom);
            banners.registerAd("gameplay", PaperTossThirdParty.query("MOPUB", this.m_ad_storefront_type, this.m_ad_device_type, "GAMEPLAY_BANNER"), BFSBannerAdActivityAddon.BannerPosition.BannerPositionTop);
            banners.prepareAdWithTag("main_menu");
            banners.showAdWithTag("main_menu");
            banners.prepareAdWithTag("gameplay");
            BFSMoPubInterstitialAdAddon interstitials = (BFSMoPubInterstitialAdAddon) this.m_addons.get(BFSMoPubInterstitialAdAddon.NAME);
            if (interstitials != null) {
                interstitials.registerAd("gameplay_interstitial", PaperTossThirdParty.query("MOPUB", this.m_ad_storefront_type, this.m_ad_device_type, "GAMEPLAY_INTERSTITIAL"), BFSInterstitialAdActivityAddon.InterstitialType.GameplayInterstitial);
                interstitials.prepareAdWithTag("gameplay_interstitial");
            }
            if (this.m_splashImage != null) {
                this.m_layout.removeView(this.m_splashImage);
                this.m_splashImage = null;
            }
            this.m_glView = new PapertossGLSurfaceView(this);
            this.m_glView.setRenderMode(1);
            if (Globals.soundMgr != null) {
                Globals.soundMgr.startBackgroundSound();
            }
            handleEventSubscriptions(true);
            RelativeLayout.LayoutParams p0 = new RelativeLayout.LayoutParams(-1, -1);
            this.m_layout.addView(this.m_glView, 0, p0);
        }
    }

    @Override // com.backflipstudios.android.engine.app.addons.BFSInterstitialAdActivityAddon.ActivityListener
    public void adDidShow(String tag) {
        BFSDebug.i("PaperTossActivity.adDidShow()");
        this.m_adDidShow = true;
    }

    @Override // com.backflipstudios.android.engine.app.addons.BFSInterstitialAdActivityAddon.ActivityListener
    public void adWillShow(String tag) {
        BFSDebug.i("PaperTossActivity.adWillShow()");
        this.m_adWillShow = true;
    }

    @Override // com.backflipstudios.android.engine.app.addons.BFSInterstitialAdActivityAddon.ActivityListener
    public void adWillNotShow(String tag) throws Throwable {
        BFSDebug.i("PaperTossActivity.adWillNotShow()");
        startGame();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.m_startedGame && keyCode == 4) {
            if (Papertoss.state == Papertoss.GameState.LEVEL) {
                Evt.getInstance().publish("paperTossPlaySound", "Crumple.wav");
            } else if (Papertoss.state == Papertoss.GameState.SCORE) {
                Evt.getInstance().publish("paperTossPlaySound", "Computer.wav");
            } else if (Papertoss.state == Papertoss.GameState.MENU) {
                Evt.getInstance().publish("paperTossPlaySound", "Crumple.wav");
                super.onKeyDown(keyCode, event);
            }
            if (this.m_glView != null) {
                this.m_glView.queueEvent(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Evt.getInstance().publish("gotoMenu");
                    }
                });
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ExitPressed implements EvtListener {
        private ExitPressed() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            PaperTossActivity.this.finish();
        }
    }

    private class ShowGameplayAds implements EvtListener {
        private ShowGameplayAds() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Globals.mainHandler.post(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.ShowGameplayAds.1
                @Override // java.lang.Runnable
                public void run() {
                    Set<String> keys = PaperTossActivity.this.m_addons.keySet();
                    for (String key : keys) {
                        BFSActivityAddon addon = (BFSActivityAddon) PaperTossActivity.this.m_addons.get(key);
                        if (addon.getType() == BFSActivityAddon.AddonType.BannerAds) {
                            ((BFSBannerAdActivityAddon) addon).showAdWithTagDelayed("gameplay", 1000L);
                            return;
                        }
                    }
                }
            });
        }
    }

    private class GoToMenu implements EvtListener {
        private GoToMenu() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Globals.mainHandler.post(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.GoToMenu.1
                @Override // java.lang.Runnable
                public void run() {
                    Set<String> keys = PaperTossActivity.this.m_addons.keySet();
                    Iterator i$ = keys.iterator();
                    while (true) {
                        if (!i$.hasNext()) {
                            break;
                        }
                        String key = i$.next();
                        BFSActivityAddon addon = (BFSActivityAddon) PaperTossActivity.this.m_addons.get(key);
                        if (addon.getType() == BFSActivityAddon.AddonType.BannerAds) {
                            ((BFSBannerAdActivityAddon) addon).showAdWithTagDelayed("main_menu", 500L);
                            break;
                        }
                    }
                    PaperTossActivity.this.showGameplayInterstitialAd();
                }
            });
        }
    }

    private class HideAds implements EvtListener {
        private HideAds() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Globals.mainHandler.post(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.HideAds.1
                @Override // java.lang.Runnable
                public void run() {
                    Set<String> keys = PaperTossActivity.this.m_addons.keySet();
                    for (String key : keys) {
                        BFSActivityAddon addon = (BFSActivityAddon) PaperTossActivity.this.m_addons.get(key);
                        if (addon.getType() == BFSActivityAddon.AddonType.BannerAds) {
                            ((BFSBannerAdActivityAddon) addon).hideBannerAds();
                            return;
                        }
                    }
                }
            });
        }
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeGroup(0);
        menu.add(0, 1, 1, Papertoss.getSound() ? "Turn Sound Off" : "Turn Sound On").setIcon(Papertoss.getSound() ? R.drawable.ic_menu_sound_on : R.drawable.ic_menu_sound_off);
        if (Papertoss.state == Papertoss.GameState.LEVEL) {
            menu.add(0, 2, 2, "Submit Score").setIcon(R.drawable.ic_menu_share);
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == 1) {
            this.m_glView.queueEvent(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    boolean sound = Papertoss.getSound();
                    Papertoss.setSound(!sound);
                    boolean sound2 = Papertoss.getSound();
                    Evt.getInstance().publish("setSound", Boolean.valueOf(sound2));
                    Evt.getInstance().publish("paperTossPlaySound", "Crumple.wav");
                }
            });
        } else if (item.getItemId() == 2) {
            this.m_glView.queueEvent(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    Evt.getInstance().publish("paperTossPlaySound", "Crumple.wav");
                    Evt.getInstance().publish("scorePrompt");
                }
            });
        }
        return true;
    }
}
