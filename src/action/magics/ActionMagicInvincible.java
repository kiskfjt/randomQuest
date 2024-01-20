package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.IO;

public class ActionMagicInvincible extends ActionMagic {

	public ActionMagicInvincible(Chr me) {
		super(me);
		name = "鋼鉄化";
		MPCons = 10;
	}

	// 効果対象：味方全体
	public boolean playerTarget() {
		me.SPDNext = me.MAX_SPD * 2;
		return IO.selectAllTargets(me.party.member, me);
	}

	// 味方全員ダメージ無効化＆行動不可
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sたちの体が鋼鉄に変化した！", me.name);
		
		IO.clearAllActions(me);
		
		for (Chr chr : me.targets) {
			chr.DEFNext = 999999999;
		}
	}

}
