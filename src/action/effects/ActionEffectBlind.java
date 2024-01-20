package action.effects;

import action.ActionEffect;
import chr.Chr;
import others.IO;

public class ActionEffectBlind extends ActionEffect {

	public ActionEffectBlind(Chr me) {
		super(me);
		name = "盲目";
	}

	// 対象なし
	public boolean playerTarget() {
		return true;
	}

	// 行動できないメッセージを出力
	public void execute() {
		IO.msgln("%sは目が眩んで動けない！", me.name);
	}

}
