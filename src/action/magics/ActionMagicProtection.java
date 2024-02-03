package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicProtection extends ActionMagic {	
	
	public ActionMagicProtection(Chr me) {
		super(me);
		name = "プロテクション";
		MPCons = 10;
		buffNo = BUFF_DEF;
		buffValue = 0.35;
	}
	
	// バフ対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}
	
	// 防御バフ：baseDEFの35%アップ
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		Calc.singleBuff(me, buffNo, buffValue);
		
		me.MP -= MPCons;
	}
}