package org.byters.periodictablelauncher.view.util;

import org.byters.periodictablelauncher.model.FileEnum;

public interface IHelperStorage {

    <T> T readFile(FileEnum fileEnum, Class<T> tClass);

    void writeData(Object data, FileEnum fileEnum);
}
