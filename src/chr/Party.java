package chr;

import java.util.ArrayList;

import chr.jobs.BraveChr;
import chr.jobs.PriestChr;
import equipment.Equipment;
import equipment.EquipmentFireClaw;
import equipment.EquipmentGoldBracelet;
import equipment.EquipmentLeatherHat;
import equipment.EquipmentLidOfPod;
import equipment.EquipmentNomalClothes;
import item.ItemMedHerb;
import item.ItemPhoenixTail;
import others.IO;

public class Party {
	public static final int PARTY_KIND_ALLY = 0;
	public static final int PARTY_KIND_ENEMY = 1;
	
	private final int MAX_ITEM_NUMBER = 10;
	private final int LV_MAX_VALUE = 100;
	private final int MULTI_BY_LV_DEFAULT = 100;
	
	public ArrayList<Chr> member;
	public Party enemy;
	public int pKind;
	
	public Party(String[] name, int partyKind) {
		member = new ArrayList<>();
		for (int i = 0; i < name.length; i++) {
			Chr chr = null;
			// ジョブをランダム選択する
			chr = selectJob(name[i]);
			
			// レベルをランダム選択する
			chr.Lv = IO.randomNum(LV_MAX_VALUE);
			setStatus(chr);
			
			// そうびをランダム選択する
			selectEquipment(chr);
			
			// どうぐをランダム選択する
			selectItem(chr);
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				pKind = PARTY_KIND_ALLY;
			} else {
				pKind = PARTY_KIND_ENEMY;
			}
			
			member.add(chr);
			chr.party = this;
			
			
			//System.out.println(member.get(i).name);
			//System.out.println(battle.party.member.get(i).ATK);
		}
	}
	
	public Party(String[] name, int partyKind, Chr[] enemyChr) {
		member = new ArrayList<>();
		Chr chr = null;
		for (int i = 0; i < name.length; i++) {
			chr = enemyChr[i];
			chr.name = name[i];
			
			member.add(chr);
			chr.party = this;
		}
	}
	
	public boolean isZenmetsu() {
		for (Chr c : member) {
			if (c.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	private Chr selectJob(String name) {
		Chr chr = null;
		int jobNo = IO.randomNum(1);// ジョブ数-1を記入
		switch (jobNo) {
		case 0:
			chr = new BraveChr(name);
			break;
		case 1:
			chr = new PriestChr(name);
			break;
		}
		return chr;
	}
	
	private void selectItem(Chr chr) {
		for (int i = 0; i < MAX_ITEM_NUMBER; i++) {
			int sumOfItems = 2;
			int itemNo = IO.randomNum(sumOfItems - 1);
			
			switch (itemNo) {
			case 0:
				chr.items.add(new ItemMedHerb(chr));
				break;
			case 1:
				chr.items.add(new ItemPhoenixTail(chr));
				break;
			}
		}
	}
	
	private void selectEquipment(Chr chr) {
		chr.equipments.add(selectWeapon(chr));
		chr.equipments.add(selectArmor(chr));
		chr.equipments.add(selectShield(chr));
		chr.equipments.add(selectHelmet(chr));
		chr.equipments.add(selectAccessory(chr));
	}
	
	private Equipment selectWeapon(Chr chr) {
		int weaponNo = IO.randomNum(0);
		
		switch (weaponNo) {
		case 0:
			return new EquipmentFireClaw(chr);
		}
		return null;
	}
	
	private Equipment selectArmor(Chr chr) {
		int armorNo = IO.randomNum(0);
		
		switch (armorNo) {
		case 0:
			return new EquipmentNomalClothes(chr);
		}
		return null;
	}
	
	private Equipment selectShield(Chr chr) {
		int sldNo = IO.randomNum(0);
		
		switch (sldNo) {
		case 0:
			return new EquipmentLidOfPod(chr);
		}
		return null;
	}
	
	private Equipment selectHelmet(Chr chr) {
		int hlmNo = IO.randomNum(0);
		
		switch (hlmNo) {
		case 0:
			return new EquipmentLeatherHat(chr);
		}
		return null;
	}
	
	private Equipment selectAccessory(Chr chr) {
		int accNo = IO.randomNum(0);
		
		switch (accNo) {
		case 0:
			return new EquipmentGoldBracelet(chr);
		}
		return null;
	}
	
	private void setStatus(Chr chr) {
		chr.HP = chr.maxHP += chr.maxHP * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MP = chr.maxMP += chr.maxMP * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.ATK = chr.baseATK += chr.baseATK * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.DEF = chr.baseDEF += chr.baseDEF * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MAT = chr.baseMAT += chr.baseMAT * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MDF = chr.baseMDF += chr.baseMDF * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.SPD = chr.baseSPD += chr.baseSPD * chr.Lv / MULTI_BY_LV_DEFAULT;
	}
}
