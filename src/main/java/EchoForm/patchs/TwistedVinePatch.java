package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

/**
 * TwistedVinePatch - 修改房间类型
 * 当玩家拥有TwistedVine遗物时，将进入的事件房间替换为普通怪物房间
 */
@SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition",paramtypez = {})
public class TwistedVinePatch {
    public  static  boolean TwistVineOn=false;
    @SpirePrefixPatch
    public static void Prefix(AbstractDungeon __instance) {
        // 检查玩家是否拥有TwistedVine遗物
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("echoForm:TwistedVine")) {
            // 检查下一个房间是否是事件房间
            if (AbstractDungeon.nextRoom != null && AbstractDungeon.nextRoom.room instanceof EventRoom) {
                // 创建新的普通怪物房间
                MonsterRoom monsterRoom = new MonsterRoom();
                
                // 更新地图节点上的房间
                AbstractDungeon.nextRoom.setRoom(monsterRoom);
                TwistVineOn=true;
            }else {
                TwistVineOn=false;
            }
        }
    }
}
