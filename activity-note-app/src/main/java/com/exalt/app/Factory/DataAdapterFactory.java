package com.exalt.app.Factory;

import com.exalt.app.utils.adapter.IDataAdapter;
import com.exalt.app.utils.adapter.JsoupDataAdapter;

public class DataAdapterFactory {


    public IDataAdapter getAdapter(String adapterType) {
        if (adapterType == null) {
            return null;
        }
        if (adapterType.equalsIgnoreCase("JSOUP")) {
            return new JsoupDataAdapter();
        }
        return null;
    }
}
