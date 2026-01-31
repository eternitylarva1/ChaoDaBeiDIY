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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


import static com.megacrit.cardcrawl.core.Settings.language;
import static com.megacrit.cardcrawl.core.Settings.seed;


@SpireInitializer
public class echoForm implements PreMonsterTurnSubscriber,StartActSubscriber,PostDungeonInitializeSubscriber,PostInitializeSubscriber,EditKeywordsSubscriber,OnStartBattleSubscriber, PostBattleSubscriber , EditStringsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, OnPlayerTurnStartSubscriber, PostExhaustSubscriber, PostDrawSubscriber { // 实现接口
    public echoForm() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
    }
    public static int turn=0;
    public static int exhaustCount=0; // 每回合消耗卡牌的计数
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
        new AutoAdd(MyModID)
            .packageFilter(AbsoluteZero.class)
            .any(AbstractCard.class, (info, card) -> {
                BaseMod.addCard(card);
                UnlockTracker.markCardAsSeen(card.cardID);
            });
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
   BaseMod.loadCustomStringsFile(UIStrings.class, "echoFormResources/localization/" + lang + "/ui.json");

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
        // 每回合开始时重置消耗计数
        exhaustCount = 0;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }


    @Override
    public void receivePostDungeonInitialize() {

    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
        // 增加消耗计数
        exhaustCount++;
        
        // 当有卡牌被消耗时，检查手牌中的BurningSword并减少其费用
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof BurningSword) {
                    BurningSword burningSword = (BurningSword) c;
                    if (burningSword.costForTurn > 0) {
                        burningSword.costForTurn--;
                        burningSword.isCostModified = true;
                    }
                }
            }
        }
    }

    @Override
    public void receivePostDraw(AbstractCard card) {
        // 当抽到BurningSword时，根据消耗次数减少费用
        if (card instanceof BurningSword) {
            BurningSword burningSword = (BurningSword) card;
            // 根据消耗次数减少费用，但不少于0
            int reduction = Math.min(exhaustCount, burningSword.costForTurn);
            burningSword.costForTurn -= reduction;
            if (reduction > 0) {
                burningSword.isCostModified = true;
            }
        }
    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        if (AbstractDungeon.player.hasRelic(OliveBranch.ID)&&AbstractDungeon.player.getRelic(OliveBranch.ID).counter>0){
            boolean allMonstersFullHealth = true;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.currentHealth < m.maxHealth) {
                    allMonstersFullHealth = false;
                    break;
                }
            }
            return !allMonstersFullHealth;
        }
        return true;

    }
}