package com.ly.design.message.bus.design1.apply;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class FileChangeEvent {
    private final Path path;
    private final WatchEvent.Kind<?>  kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }
}