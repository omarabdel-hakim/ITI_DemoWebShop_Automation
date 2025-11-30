package Drivers;

public enum Browsers
{
    CHROME{
        @Override
        public AbstractDriver getDriverFactory()
        {
            return new ChromeFactory();
        }
    },
    EDGE{
        @Override
        public AbstractDriver getDriverFactory()
        {
            return new EdgeFactory();
        }
            };

    public abstract AbstractDriver getDriverFactory();
    }
