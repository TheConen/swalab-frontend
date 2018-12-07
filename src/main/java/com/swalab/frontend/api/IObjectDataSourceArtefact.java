package com.swalab.frontend.api;

import java.util.Comparator;

/**
 * this interfaces offers a common way for addressing elements that have a name or something similar
 */
public interface IObjectDataSourceArtefact<T>  extends INamedArtefact{

    Comparator<T> getComparator();
}
