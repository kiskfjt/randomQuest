package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.IO;

public class ActionMagicSnoozeAll extends ActionMagic {

	public ActionMagicSnoozeAll(Chr me) {
		super(me);
		name = "ラリポーマ";
		MPCons = 10;
		successRate = 50;
		statusNo = Chr.STATUS_ASLEEP;
	}

	// 対象：敵全体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// 効果：敵単体を確率で睡眠状態にする
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		IO.changeMultiStatus(me, statusNo);	
		
		me.MP -= MPCons;
	}

}
