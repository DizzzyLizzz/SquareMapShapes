package com.dizzzylizzz.sms;

import xyz.jpenilla.squaremap.api.SimpleLayerProvider;

public class layerProvider {


    public static SimpleLayerProvider markerLayer() {

        return SimpleLayerProvider.builder("SMShapesMod")
                .showControls(true)
                .defaultHidden(false)
                .layerPriority(1)
                .zIndex(250)
                .build();


}}
