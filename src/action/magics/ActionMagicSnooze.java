package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.IO;

public class ActionMagicSnooze extends ActionMagic {

	public ActionMagicSnooze(Chr me) {
		super(me);
		name = "ラリポー";
		MPCons = 5;
		successRate = 50;
		statusNo = me.STATUS_ASLEEP;
	}

	// 対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// 効果：敵単体を確率で睡眠状態にする
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		if (IO.probability(successRate)) {
			IO.changeSingleStatus(me, statusNo);
		} else {
			IO.msgln("しかし%sには効かなかった！", me.targets.get(0).name);
		}
		
		me.MP -= MPCons;
	}

}
