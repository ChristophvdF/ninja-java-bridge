ninja-java-bridge
==========

Allows you to write NinjaBlocks drivers in Java

A basic example looks something like this (subject to change...)

```
public class ElliotsTestDriver extends BaseNinjaDriver implements NinjaDriver {

    public ElliotsTestDriver(NinjaClient app) {
        app.on("client::up", new JsonEventHandler() {

            public void handle(String event, ArrayNode args) {
                register(new ElliotsTestDevice());
            }

        });
    }

}
```

```
public class ElliotsTestDevice extends BaseNinjaDevice implements NinjaDevice {

    public ElliotsTestDevice() {
        super(0, 14, "test123", "Test Device");
    }

    public boolean readable() {return true;}

    public boolean writable() {return true;}

    public boolean configurable() {return false;}

}

```
