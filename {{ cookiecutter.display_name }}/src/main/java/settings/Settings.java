package {{ cookiecutter.__package }}.settings;

import me.raviel.core.configuration.Config;
import me.raviel.core.configuration.ConfigSetting;
import {{ cookiecutter.__package }}.{{ cookiecutter.display_name }};

public class Settings {
    static final Config config = {{ cookiecutter.display_name }}.getInstance().getCoreConfig();
    public static final ConfigSetting LOCALE_FILE = new ConfigSetting(config,
            "System.Language Mode", "default",
            "The enabled language file.");

{%- if cookiecutter.database == "yes" -%}
    public static final ConfigSetting
            DATABASE_DRIVER = new ConfigSetting(config, "Database.Driver",
                "SQLITE",
                "Databse Driver: MYSQL, MARIADB, SQLITE"),
            DB_HOSTNAME = new ConfigSetting(config, "Database.Hostname",
                    "localhost"),
            DB_PORT = new ConfigSetting(config, "Database.Port",
                    3306),
            DB_DATABASE = new ConfigSetting(config, "Database.Database",
                    "your-database"),
            DB_USERNAME = new ConfigSetting(config, "Database.Username",
                    "user"),
            DB_PASSWORD = new ConfigSetting(config, "Database.Password",
                    "pass"),
            DB_USE_SSL = new ConfigSetting(config, "Database.Use SSL",
                    false),
            DB_POOL_SIZE = new ConfigSetting(config, "Database.Pool Size",
                    3,
                    "Determines the number of connections the pool is using.",
                    "Increase this value if you are getting timeout errors when more players online.");
{% endif %}

    public static void setupConfig() {
        config
                .setDefaultComment("Main", "General settings and options.");
        config.load();
        config.
                setAutoremove(true).
                setAutosave(true);

        config.saveChanges();
    }
}
