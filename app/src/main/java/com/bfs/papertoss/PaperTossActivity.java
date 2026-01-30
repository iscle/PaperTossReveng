package com.bfs.papertoss;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bfs.papertoss.cpp.Papertoss;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Globals;
import com.iscle.papertoss.R;

/* JADX INFO: loaded from: classes.dex */
public class PaperTossActivity extends Activity {
    private static final String TAG = "PaperTossActivity";
    private ExitPressed exitPressed;
    private PapertossGLSurfaceView m_glView = null;
    private ImageView m_splashImage = null;
    private RelativeLayout m_layout = null;
    private boolean m_startedGame = false;

    public PaperTossActivity() {
        this.exitPressed = new ExitPressed();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "PaperTossActivity.onCreate");
        super.onCreate(savedInstanceState);
        Globals.m_activity = this;
        buildUserInterface();
        this.m_startedGame = false;
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
        Log.i(TAG, "PaperTossActivity.onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    public void onResume() {
        Log.i(TAG, "PaperTossActivity.onResume");
        super.onResume();
        if (!this.m_startedGame) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // from class: com.bfs.papertoss.PaperTossActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    PaperTossActivity.this.startGame();
                }
            }, 2000L);
            return;
        }
        if (this.m_glView != null) {
            this.m_glView.onResume();
        }
        if (Globals.soundMgr != null) {
            Globals.soundMgr.startBackgroundSound();
        }
    }

    @Override // android.app.Activity
    public void onPause() {
        Log.i(TAG, "PaperTossActivity.onPause");
        super.onPause();
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
        Log.i(TAG, "PaperTossActivity.onStop");
        super.onStop();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        Log.i(TAG, "PaperTossActivity.onDestroy");
        super.onDestroy();
        handleEventSubscriptions(false);
    }

    private void handleEventSubscriptions(boolean subscribe) {
        Evt evt = Evt.getInstance();
        if (subscribe) {
            evt.subscribe("onExitPressed", this.exitPressed);
            return;
        }
        evt.unsubscribe("onExitPressed", this.exitPressed);
    }

    private void startGame() {
        if (!this.m_startedGame) {
            Log.i(TAG, "PaperTossActivity.startingGame()");
            this.m_startedGame = true;
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
