package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicBreakShield extends ActionMagic {

	public ActionMagicBreakShield(Chr me) {
		super(me);
		name = "ブレイクシールド";
		MPCons = 10;
		buffNo = BUFF_DEF;
		buffValue = -0.25;
	}
	
	// デバフ対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// 防御デバフ：baseDEFの25%ダウン
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		Calc.singleBuff(me, buffNo, buffValue);
		
		me.MP -= MPCons;
	}
}
