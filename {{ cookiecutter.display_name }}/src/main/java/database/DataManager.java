package {{ cookiecutter.__package }}.database;

import me.raviel.core.database.DataManagerAbstract;
import me.raviel.core.database.DatabaseConnector;
import org.bukkit.plugin.Plugin;

public class DataManager extends DataManagerAbstract {
    public DataManager(DatabaseConnector databaseConnector, Plugin plugin) {
        super(databaseConnector, plugin);
    }
}
