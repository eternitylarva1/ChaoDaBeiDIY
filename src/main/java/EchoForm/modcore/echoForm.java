package EchoForm.modcore;


import EchoForm.relics.*;
import EchoForm.cards.*;
import EchoForm.potions.*;
import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


import static com.megacrit.cardcrawl.core.Settings.language;
import static com.megacrit.cardcrawl.core.Settings.seed;


@SpireInitializer
public class echoForm implements StartActSubscriber,PostDungeonInitializeSubscriber,PostInitializeSubscriber,EditKeywordsSubscriber,OnStartBattleSubscriber, PostBattleSubscriber , EditStringsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, OnPlayerTurnStartSubscriber { // 实现接口
    public echoForm() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
    }
    public static int turn=0;
    public static final String MyModID = "Chaodabei";
    ModPanel settingsPanel = new ModPanel();
    public static SpireConfig config;
    public static boolean hasselected=false;
    public static boolean isfakefire;
    public static HashMap<Integer,Boolean> firemap=new HashMap<>();

    public static void initialize() throws IOException {

        new echoForm();


    }

    // 当basemod开始注册mod卡牌时，便会调用这个函数

    @Override
    public void receiveStartAct() {

    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(MyModID)
            .packageFilter(DemonBlood.class)
            .any(AbstractRelic.class, (info, relic) -> {
                BaseMod.addRelic(relic, RelicType.SHARED);

                    UnlockTracker.markRelicAsSeen(relic.relicId);

            });
    }

    @Override
    public void receiveEditCards() {
        /*
        BaseMod.addCard(new Bloodfall());
        BaseMod.addCard(new BurningSword());
        BaseMod.addCard(new Neurotoxin());
        BaseMod.addCard(new MachineSilence());
        BaseMod.addCard(new UltimateCompute());
        BaseMod.addCard(new AbsoluteZero());
        BaseMod.addCard(new BoneScythe());
        BaseMod.addCard(new NightHunt());
        BaseMod.addCard(new MachineRage());
        BaseMod.addCard(new MachineFormat());
        BaseMod.addCard(new SleeveDanceMeteor());*/
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
    BaseMod.loadCustomStringsFile(RelicStrings.class, "echoFormResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "echoFormResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "echoFormResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, "echoFormResources/localization/" + lang + "/potions.json");

    }
    public static float getYPos(float y) {
        return Settings.HEIGHT/(2160/y);
    }
    public static float getXPos(float x) {
        return Settings.WIDTH/(3840/x);
    }
    @Override
    public void receivePostInitialize() {

    }



    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }
   public static void initializeHashmap(){
        if (AbstractDungeon.player==null|| !CardCrawlGame.isInARun()){
            return;
        }
       com.megacrit.cardcrawl.random.Random rng=new com.megacrit.cardcrawl.random.Random(seed);

        for(int i=0;i<1000;i++){
            boolean istrue;
            istrue=rng.randomBoolean(0.7f);
firemap.put(i,istrue);

        }
   }
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        }

        String json = Gdx.files.internal("echoFormResources/localization/" + lang + "/keywords.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(MyModID, keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }

    }

    @Override
    public void receiveOnPlayerTurnStart() {


    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }


    @Override
    public void receivePostDungeonInitialize() {

    }
}