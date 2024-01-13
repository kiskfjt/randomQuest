package equipment;

import chr.Chr;
import others.IO;

public class EquipmentRubyOfStrength extends Equipment {

	public EquipmentRubyOfStrength(Chr me) {
		super(me);
		name = "力のルビー";
		ATK = 10;
		addStatus();
	}

	// 特殊効果なし
	public boolean playerTarget() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	// 特殊効果なし
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		IO.msgln("しかし、なにもおこらなかった！");
	}

}
