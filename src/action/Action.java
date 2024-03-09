package action;

import java.util.function.Supplier;

import chr.Chr;

public abstract class Action {
	public static final double MULTI_DEFAULT_VALUE = 1.0;
	public static final double ATK_NEXT_DEFAULT_VALUE = 1.0;
	public static final double DEF_NEXT_DEFAULT_VALUE = 1.0;
	public static final double RANGE_MIN_DEFAULT_VALUE = 0.8;
	public static final double RANGE_MAX_DEFAULT_VALUE = 1.2;
	public static final int SUCCESS_RATE_DEFAULT = 100;
	
	private final int MISS_RATE_DEFAULT = 5;
	private final int CRITICAL_RATE_DEFAULT = 5;
	
	// public Runnable target;
	public Supplier<Boolean> target;
	public String name;
	public abstract boolean playerTarget();
	public abstract void execute();
	public Chr me;
	
	// 攻撃、防御、回復、バフの倍率
	public double multi = MULTI_DEFAULT_VALUE;
	
	// 攻撃の倍率(1回のみ)
	public double ATKNext = ATK_NEXT_DEFAULT_VALUE;
	
	// 防御の倍率(1回のみ)
	public double DEFnext = DEF_NEXT_DEFAULT_VALUE;
	
	// 攻撃、回復等の範囲の最低値、最大値
	public double rangeMin = RANGE_MIN_DEFAULT_VALUE;
	public double rangeMax = RANGE_MAX_DEFAULT_VALUE;
	public int rangeMinInt;
	public int rangeMaxInt;
	
	// バフの種類
	public int buffNo;
	public static final int BUFF_ATK = 0;
	public static final int BUFF_DEF = 1;
	public static final int BUFF_MAT = 2;
	public static final int BUFF_MDF = 3;
	public static final int BUFF_SPD = 4;
	// バフ効果量
	public double buffValue;
	
	// その他効果量
	public double effectValue;
	
	// ステータスの種類
	public static final int STATUS_ATK = 0;
	public static final int STATUS_DEF = 1;
	public static final int STATUS_MAT = 2;
	public static final int STATUS_MDF = 3;
	public static final int STATUS_SPD = 4;
	
	// 消費MP
	public int MPCons;
	
	// 成功率
	public int successRate = SUCCESS_RATE_DEFAULT;
	
	// ミス率
	public int missRate = MISS_RATE_DEFAULT;
	
	// 会心痛恨率
	public int criticalRate = CRITICAL_RATE_DEFAULT;
	
	// アクションの種類の番号
	public int actionType;
	
	// 隠しアクション番号
	public final int ACTION_NOT_SPECIAL = 0;
	public final int ACTION_SPECIAL_LOW_HP = 1;
	// 隠しアクション条件
	public double actionSpecialHPCondition;
	// 隠しアクションデフォルト設定
	public int specialAction = ACTION_NOT_SPECIAL;
	
	// 属性番号
	public static final int ACTION_ELEMENT_NOMAL = 0;
	public static final int ACTION_ELEMENT_FIRE = 1;
	public static final int ACTION_ELEMENT_WATER = 2;
	public static final int ACTION_ELEMENT_THUNDER = 3;
	public static final int ACTION_ELEMENT_EARTH = 4;
	public static final int ACTION_ELEMENT_AIR = 5;
	public static final int ACTION_ELEMENT_LIGHT = 6;
	public static final int ACTION_ELEMENT_DARK = 7;
	// 属性デフォルト設定
	public int element = ACTION_ELEMENT_NOMAL;
	
	// アクションの種類
	public final int ACTION_TYPE_SLASH = 1;
	public final int ACTION_TYPE_BLOW = 2;
	public final int ACTION_TYPE_DANCE = 3;
	
	// 状態変化の番号
	public int statusNo;
	
	public Action(Chr me) {
		this.me = me;
		this.target = this::playerTarget;
	}
}
