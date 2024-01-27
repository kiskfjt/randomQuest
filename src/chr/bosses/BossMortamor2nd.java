package chr.bosses;

import action.Action;
import action.ActionBasicAttack;
import action.functions.ActionFunctionToBrot;
import action.magics.ActionMagicProtection;
import action.skills.ActionSkillHardDefense;
import action.skills.ActionSkillRush;
import chr.Chr;
import others.IO;

public class BossMortamor2nd extends Chr {
	private final int A_ATTACK = 0;
	private final int A_RUSH = 1;
	private final int A_RUSH2 = 2;
	private final int A_PROTECTION = 3;
	private final int A_HARD_DEFENSE = 4;
	private final int A_TO_BROT = 5;
	
	private final int A_KIAITAME = 0;
	private final int A_BREAK_SHIELD = 1;
	private final int A_SHIPPU = 2;
	private final int A_MAWASHIGERI = 3;
	private final int A_FIREBREATH = 4;
	private final int A_TO_AROT = 5;
	
	private final int A_TURN_DEFAULT = 2;
	
	public int BrotNo;
	
	public BossMortamor2nd() {
		this("");
	}
	
	public BossMortamor2nd(String name) {
		super(3000, 200, 100, 60, 100, 60, 40);
		
		jobName = "Mortamor2nd";
		
		// 初期設定はAローテ
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionSkillRush(this));
		actions.add(new ActionSkillRush(this));
		actions.add(new ActionMagicProtection(this));
		actions.add(new ActionSkillHardDefense(this));
		actions.add(new ActionFunctionToBrot(this));
		
		// 耐性設定
		this.canLowerATK = false;
		this.canLowerDEF = false;
		this.canLowerMAT = false;
		this.canLowerMDF = false;
		this.canLowerSPD = false;
		this.canLowerMagicResistance = false;
		
		this.actionTurnDefault = A_TURN_DEFAULT;
		
		setToNPC();
	}

	private void setToNPC() {
		command = this::nonPlayerCommand;
		actions.get(A_ATTACK).target = this::attackTarget;
		actions.get(A_RUSH).target = this::rushTarget;
		actions.get(A_RUSH2).target = this::rush2Target;
		actions.get(A_PROTECTION).target = this::protectionTarget;
		actions.get(A_HARD_DEFENSE).target = this::hardDefenseTarget;
		actions.get(A_TO_BROT).target = this::toBrotTarget;
	}
	
	public boolean nonPlayerCommand() {
		int actionNum = 0;
		
		// Aローテのとき
		if (actions.get(0) instanceof ActionBasicAttack) {
			while (true) {
				actionNum = IO.randomNum(A_TO_BROT);
				if (actionNum == A_PROTECTION && IO.checkBuff(Action.STATUS_DEF, this.party.member)) {
					continue;
				} else if (actionNum == A_HARD_DEFENSE && this.DEFNext != this.DEF_MULTI_DEFAULT) {
					continue;
				} else {
					break;
				}
			}
		// Bローテのとき
		} else {
			while (true) {
				actionNum = this.BrotNo * 2 + IO.randomNum(1);
				if (actionNum == A_BREAK_SHIELD && IO.checkBuff(Action.STATUS_DEF, this.party.enemy.member)) {
					continue;
				} else {
					break;
				}
			}
			this.BrotNo++;
			if (this.BrotNo == 3) {
				this.BrotNo = 0;
			}
		}
		
		action = actions.get(actionNum);
		return action.target.get();
	}
	
	// Aローテのターゲット選択メソッド
	public boolean attackTarget() {
		return IO.selectSingleRandomTarget(this.party.enemy.member, this);
	}
	
	public boolean rushTarget() {
		return IO.selectSingleRandomTarget(this.party.enemy.member, this);
	}
	
	public boolean rush2Target() {
		return IO.selectSingleRandomTarget(this.party.enemy.member, this);
	}
	
	public boolean protectionTarget() {
		this.targets.add(this);
		return true;
	}
	
	public boolean hardDefenseTarget() {
		this.DEFNext = actions.get(A_HARD_DEFENSE).multi;
		targets.clear();
		return true;
	}
	
	public boolean toBrotTarget() {
		targets.clear();
		return true;
	}
	
	// Bローテのターゲット選択メソッド
	public boolean kiaitameTarget() {
		targets.clear();
		return true;
	}
	
	public boolean breakShieldTarget() {
		return IO.selectAllTargets(this.party.enemy.member, this);
	}
	
	public boolean shippuTarget() {
		this.SPDNext = this.MAX_SPD;
		return IO.selectSingleRandomTarget(this.party.enemy.member, this);
	}
	
	public boolean mawashigeriTarget() {
		return IO.selectAllTargets(this.party.enemy.member, this);
	}
	
	public boolean fireBreathTarget() {
		return IO.selectAllTargets(this.party.enemy.member, this);
	}
	
	public boolean toArotTarget() {
		this.targets.clear();
		return true;
	}
}
