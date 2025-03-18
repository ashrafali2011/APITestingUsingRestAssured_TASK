package com.petroapp.hiring;

public class SessionManager {
    private static String sessionId;

    // this is asetter to save session_id
    public static String getSessionId() {
        return sessionId;
    }

    // this is agetter to get the saved session_id when needed
    public static void setSessionId(String id) {
        sessionId = id;
    }
}
