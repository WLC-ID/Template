package {{ cookiecutter.__package }}.command;

import me.raviel.core.commands.AbstractCommand;
import {{ cookiecutter.__package }}.{{ cookiecutter.display_name }};
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends AbstractCommand {

    private final {{ cookiecutter.display_name }} plugin = {{ cookiecutter.display_name }}.getInstance();

    public ReloadCommand() {
        super(CommandType.CONSOLE_OK, "reload");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        plugin.reloadConfig();
        plugin.getLocale().getMessage("general.message.reload").sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "{{ cookiecutter.__name }}.reload";
    }

    @Override
    public String getSyntax() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the configuration and language files.";
    }
}
