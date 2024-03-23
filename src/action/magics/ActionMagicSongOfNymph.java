package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicSongOfNymph extends ActionMagic {

	public ActionMagicSongOfNymph(Chr me) {
		super(me);
		name = "精霊の歌";
		MPCons = 20;
		rangeMin = 0.4;
		rangeMax = 0.6;
		successRate = 50;
	}
	
	private String statusStr = "歌唱";
	// 継続ターン数
	private int statusTurn = 1;

	// 対象：味方全体
	public boolean playerTarget() {
		me.status = Chr.STATUS_SING;
		me.statusStr = this.statusStr;
		me.statusTurn = this.statusTurn;
		
		return IO.selectAllTargets(me.party.member, me);
	}

	// 効果：味方全員の蘇生、失敗有り
	// 次のターンまで歌を歌い続け、次のターンで味方を蘇生する
	public void execute() {
		if (me.statusTurn == this.statusTurn) {
			IO.msgln("【%sの%s！】", me.name, name);
			IO.msgln("%sはいのりをこめて精霊の歌をうたいだした", me.name);
		} else if (me.statusTurn == 0) {
			IO.msgln("%sは歌を歌い終わった！", me.name);
			Calc.multiRevive(me);
			
			me.status = Chr.STATUS_NOMAL;
		}
	}

}
