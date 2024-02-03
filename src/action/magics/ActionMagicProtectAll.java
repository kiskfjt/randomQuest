package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicProtectAll extends ActionMagic {

	public ActionMagicProtectAll(Chr me) {
		super(me);
		name = "プロテクトオール";
		MPCons = 20;
		buffNo = BUFF_DEF;
		buffValue = 0.25;
	}
	
	// バフ対象：味方全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.member, me);
	}

	// 防御バフ：baseDEFの25%アップ
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		Calc.multiBuff(me, buffNo, buffValue);
		
		me.MP -= MPCons;
	}

}
