package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicBreakShieldAll extends ActionMagic {

	public ActionMagicBreakShieldAll(Chr me) {
		super(me);
		name = "ブレイクシールドオール";
		MPCons = 20;
		buffNo = BUFF_DEF;
		buffValue = -0.2;
	}
	
	// デバフ対象：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 防御デバフ：baseDEFの20%ダウン
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		Calc.multiBuff(me, buffNo, buffValue);
		
		me.MP -= MPCons;
	}
}
