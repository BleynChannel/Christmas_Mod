package com.civilization.christmas.core;

public interface Module {
    public void register();

    public void loadClient();
    public void loadServer();
    public void loadCommon();

    public void stopClient();
    public void stopServer();
    public void stopCommon();
}
