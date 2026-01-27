package com.bfs.papertoss.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Evt {
    public static final String DEFAULT_EVENT = "DEFAULT_EVENT";
    private static Evt m_instance = null;
    private HashMap<String, ArrayList<EvtListener>> m_listeners;

    private Evt() {
        this.m_listeners = null;
        this.m_listeners = new HashMap<>();
    }

    public static Evt getInstance() {
        if (m_instance == null) {
            m_instance = new Evt();
        }
        return m_instance;
    }

    public void subscribe(String event_name, EvtListener listener) throws Throwable {
        ArrayList<EvtListener> list;
        synchronized (this.m_listeners) {
            try {
                if (this.m_listeners.containsKey(event_name)) {
                    list = this.m_listeners.get(event_name);
                } else {
                    ArrayList<EvtListener> list2 = new ArrayList<>();
                    try {
                        this.m_listeners.put(event_name, list2);
                        list = list2;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
                list.add(listener);
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public void unsubscribe(String event_name, EvtListener listener) {
        synchronized (this.m_listeners) {
            if (this.m_listeners.containsKey(event_name)) {
                ArrayList<EvtListener> list = this.m_listeners.get(event_name);
                list.remove(listener);
                if (list.size() == 0) {
                    this.m_listeners.remove(event_name);
                }
            }
        }
    }

    public void publish(String event_name) {
        publish(event_name, null);
    }

    public void publish(String event_name, Object object) {
        synchronized (this.m_listeners) {
            if (this.m_listeners.containsKey(event_name)) {
                ArrayList<EvtListener> list = this.m_listeners.get(event_name);
                Iterator i$ = list.iterator();
                while (i$.hasNext()) {
                    EvtListener listener = i$.next();
                    listener.run(object);
                }
            }
        }
    }
}
