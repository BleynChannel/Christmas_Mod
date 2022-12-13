package com.civilization.christmas.modules.daily_gifts;

import com.civilization.christmas.core.Module;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

// Ежедневные подарки за вход в игру
public class DailyGifts implements Module {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void register() {
        LOGGER.info("register");
    }

    @Override
    public void loadClient() {
        LOGGER.info("loadClient");
    }

    @Override
    public void loadServer() {
        LOGGER.info("loadServer");
    }

    @Override
    public void loadCommon() {
        LOGGER.info("loadCommon");
    }

    @Override
    public void stopClient() {
        LOGGER.info("stopClient");
    }

    @Override
    public void stopServer() {
        LOGGER.info("stopServer");
    }

    @Override
    public void stopCommon() {
        LOGGER.info("stopCommon");
    }
}
