package anokaze.kazedice;

import anokaze.kazedice.command.RollCommand;
import anokaze.kazedice.constants.CommandDocs;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;

/**
 * @author AnoKaze
 */
@Slf4j
public class KazeDicePlugin extends BasePlugin {
    private static KazeDicePlugin instance;
    private static MongoClient mongoClient;

    private static final String DATABASE_NAME = "kazedice";

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        initMongodb();
        log.info("KazeDice loaded!");
    }

    @Override
    public void onEnable(){
        log.info("KazeDice connected!");
        createCommand("roll")
                .addAlias("r")
                .addOptionalArgument(String.class, "1d100")
                .executesUser(new RollCommand())
                .setDescription(CommandDocs.ROLL_COMMAND_DOC.getDescription())
                .setHelpContent(CommandDocs.ROLL_COMMAND_DOC.getHelpContent())
                .register(getInstance());
        createCommand("state")
                .addAlias("st")
                .register(getInstance());
    }

    @Override
    public void onDisable() {
        log.info("KazeDice disconnected!");
    }

    public static KazeDicePlugin getInstance() {
        return instance;
    }

    public static MongoDatabase getMongodb() {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    private JKookCommand createCommand(String rootName){
        return new JKookCommand(rootName).addPrefix(".").addPrefix("。");
    }

    private void initMongodb(){
        String mongoUri = KazeDicePlugin.getInstance().getConfig().getString("mongo.uri");
        if(mongoUri == null){
            log.error("connect to mongodb failed!");
            setEnabled(false);
            return;
        }
        mongoClient = MongoClients.create(mongoUri);
    }
}
