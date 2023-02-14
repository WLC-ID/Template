package {{ cookiecutter.__package }};

import {{ cookiecutter.__package }}.command.ReloadCommand;{% if cookiecutter.database == "yes" %}
import {{ cookiecutter.__package }}.database.DataManager;{% endif %}
import {{ cookiecutter.__package }}.settings.Settings;
import lombok.Getter;
import me.raviel.core.RLXPlugin;
import me.raviel.core.RavielLibX;{% if cookiecutter.gui == "yes" %}
import me.raviel.core.gui.GuiManager;{% endif %}
import me.raviel.core.configuration.Config;
import me.raviel.core.commands.CommandManager;
import me.raviel.core.compatibility.CompatibleMaterial;{% if cookiecutter.database == "yes" %}
import me.raviel.core.database.*;{% endif %}

import java.util.Collections;
import java.util.List;{% if cookiecutter.database == "yes" %}
import java.util.function.Consumer;
import java.util.Arrays;
{% endif %}

public final class {{ cookiecutter.display_name }} extends RLXPlugin {

    @Getter
    private static {{ cookiecutter.display_name }} instance;
{% if cookiecutter.gui == "yes" %}

    @Getter
    private final GuiManager guiManager = new GuiManager(this);
{% endif %}

    @Getter
    private final CommandManager commandManager = new CommandManager(this);
{% if cookiecutter.database == "yes" %}

    @Getter
    private DatabaseConnector databaseConnector;

    @Getter
    private DataManager dataManager;

    @Getter
    private DataMigrationManager dataMigrationManager;
{% endif %}

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        setup();

        this.commandManager.addMainCommand("{{ cookiecutter.__name }}").
                addSubCommand(new ReloadCommand());
    }

    @Override
    public void onPluginDisable() {
        disableManager();
    }

    @Override
    public void onDataLoad() {
{%- if cookiecutter.database == "yes" -%}
        try {
            String driver = Settings.DATABASE_DRIVER.getString("SQLITE");
            if (driver.equalsIgnoreCase("MYSQL") ||
                    driver.equalsIgnoreCase("MARIADB")
            ) {
                String hostname = Settings.DB_HOSTNAME.getString();
                int port = Settings.DB_PORT.getInt();
                String database = Settings.DB_DATABASE.getString();
                String username = Settings.DB_USERNAME.getString();
                String password = Settings.DB_PASSWORD.getString();
                boolean useSSL = Settings.DB_USE_SSL.getBoolean();
                int poolSize = Settings.DB_POOL_SIZE.getInt();

                if (driver.equals("MYSQL")) {
                    this.databaseConnector = new MySQLConnector(this, hostname, port, database, username, password, useSSL, poolSize);
                    this.getLogger().info("Data handler connected using MySQL.");
                } else {
                    this.databaseConnector = new MariaDBConnector(this, hostname, port, database, username, password, useSSL, poolSize);
                    this.getLogger().info("Data handler connected using MariaDB");
                }
            } else {
                this.databaseConnector = new SQLiteConnector(this);
                this.getLogger().info("Data handler connected using SQLite.");
            }
        } catch (Exception ex) {
            this.getLogger().severe("Fatal error trying to connect to database. Please make sure all your connection settings are correct and try again. Plugin has been disabled.");
            this.emergencyStop();
        }

        onDatabaseConnected();
{% endif %}
    }

    private void setup() {
        RavielLibX.registerPlugin(this, {{ cookiecutter.plugin_id }}, CompatibleMaterial.{{ cookiecutter.icon }});
        Settings.setupConfig();
        this.setLocale(Settings.LOCALE_FILE.getString("default"), false);
    }

    private void disableManager() { {% if cookiecutter.gui == "yes" %}
        this.guiManager.closeAll();{% endif %}{% if cookiecutter.database == "yes" %}
        if (this.databaseConnector != null) {
            this.databaseConnector.closeConnection();
        }
        {% endif %}
    }{% if cookiecutter.database == "yes" %}

    private void onDatabaseConnected() {
        if (this.databaseConnector == null) return;

        this.dataManager = new DataManager(databaseConnector, this);

        this.dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager
                // Insert Migration Object here
                // new Migration1(),
                // new Migration2(),
        );
        this.dataMigrationManager.runMigrations();
        List<Consumer<DataManager>> dataLoaders = Arrays.asList(

        );
        dataLoaders.forEach(each -> each.accept(this.dataManager));
    }

{% endif %}
    @Override
    public void onConfigReload() {
        this.setLocale(Settings.LOCALE_FILE.getString("default"), true);
    }

    @Override
    public List<Config> getExtraConfig() {
        return Collections.emptyList();
    }
}
