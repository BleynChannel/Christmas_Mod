package com.civilization.christmas.modules.christmas_event;

import com.civilization.christmas.core.Module;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

// Главное событие новогоднего обновления "Спасение Рождества"
/*
    Событие делиться на четыри этапа:
    - Подготовка сервера
    - Украшение рождественнской елки
    - Защита от нападения Гринча
    - Пирование

    В каждом этапе есть несколько заданий для выполнения. После выполнения всех заданий одного из этапов даются разные
    крутые подарки для каждого игрока, который сделал вклад для выполнения данного этапа.
    Задания распространяются на весь сервер. Задание может выполнить как несколько игроков, так и один игрок.
    Для каждого задания ведется своя статистика вклада каждого игрока. Чем больше или лучше игрок выполнил конкретное
    задание, тем выше он находиться в статистике (счет ведется в процентах). После выполнения задания игрокам с высоким
    вкладом на выполнения задания дается больший шанс на выпадение легендарных предметов с "Ежедневных подарков".
*/
public class ChristmasEvent implements Module {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void register() {

    }

    @Override
    public void loadClient() {

    }

    @Override
    public void loadServer() {

    }

    @Override
    public void loadCommon() {

    }

    @Override
    public void stopClient() {

    }

    @Override
    public void stopServer() {

    }

    @Override
    public void stopCommon() {

    }
}
