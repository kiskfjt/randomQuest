package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicGigaLightning extends ActionMagic {

	public ActionMagicGigaLightning(Chr me) {
		super(me);
		name = "ミナデイン";
		multi = 250;
		MPCons = 10;
	}
	
	private final int statusTurn = 0;

	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		me.SPDNext = me.MAX_SPD * 3;
		
		for (Chr chr : me.party.member) {
			// 1人でも死んでたら魔法選択に戻る
			if (chr.isDead()) {
				return false;
			}
			// 1人でもMPが足りなかっらたら魔法選択に戻る
			if (chr.MP < this.MPCons) {
				return false;
			}
		}
		
		for (Chr chr : me.party.member) {
			chr.status = me.STATUS_SKIP;
			chr.statusTurn = this.statusTurn;
		}
		
		// 自分以外のアクションをクリア
		IO.clearAllActionsExceptMe(me);
		
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		IO.msgln("【%sたちは力を合わせて%sを唱えた！】", me.name, name);
		
		Calc.mgcSingleDmg(me);
		
		for (Chr chr : me.party.member) {
			chr.MP -= MPCons;
		}
	}

}
