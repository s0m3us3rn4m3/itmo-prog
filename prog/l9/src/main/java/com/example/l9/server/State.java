package com.example.l9.server;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.example.l9.common.collection.Movie;

public class State {
    private Scanner input;
    private Map<String, Movie> collection;
    private ReadWriteLock lock;
    private ReadWriteLock historyLock;
    private Deque<String> history;
    private Set<String> executeScriptStack;
    private Connection conn;
    private String username;

    public State() {
        collection = new TreeMap<String, Movie>();
        history = new ArrayDeque<String>();
        executeScriptStack = new HashSet<String>();
        lock = new ReentrantReadWriteLock();
        historyLock = new ReentrantReadWriteLock();
    }

    public ReadWriteLock getHistoryLock() {
        return historyLock;
    }

    public void setHistoryLock(ReadWriteLock lock) {
        historyLock = lock;
    }

    public ReadWriteLock getLock() {
        return lock;
    }

    public void setLock(ReadWriteLock lock) {
        this.lock = lock;
    }

    public void setUsername(String u) {
        username = u;
    }

    public String getUsername() {
        return username;
    }

    public void setConn(Connection c) {
        conn = c;
    }

    public Connection getConn() {
        return conn;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Scanner getInput() {
        return input;
    }

    public void setCollection(Map<String, Movie> collection) {
        this.collection = collection;
    }

    public Map<String, Movie> getCollection() {
        return collection;
    }

    public void setExecuteScriptStack(Set<String> stack) {
        this.executeScriptStack = stack;
    }

    public Set<String> getExecuteScriptStack() {
        return executeScriptStack;
    }

    public void setHistory(Deque<String> history) {
        this.history = history;
    }

    public Deque<String> getHistory() {
        return history;
    }

}
