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
		buffValue = -25;
	}
	
	// デバフ対象：敵全体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	// 防御デバフ：baseDEFの50%ダウン
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);

		Calc.multiBuff(me, buffNo, buffValue);

		me.MP -= MPCons;
	}
}
