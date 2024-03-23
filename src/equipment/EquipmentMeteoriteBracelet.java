package equipment;

import chr.Chr;
import others.IO;

public class EquipmentMeteoriteBracelet extends Equipment {

	public EquipmentMeteoriteBracelet(Chr me) {
		super(me);
		name = "ほしふるうでわ";
		SPD = me.baseSPD;
		elementPointLight = 10;
		addStatus();
	}

	// 特殊効果なし
	public boolean playerTarget() {
		return true;
	}

	// 特殊効果なし
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		IO.msgln("しかし、なにもおこらなかった！");
	}

}
