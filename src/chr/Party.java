package chr;

import java.util.ArrayList;

import chr.jobs.BraveChr;
import chr.jobs.DancerChr;
import chr.jobs.FighterChr;
import chr.jobs.LuminaryChr;
import chr.jobs.PriestChr;
import chr.jobs.WarriorChr;
import equipment.Equipment;
import equipment.EquipmentAgilityRing;
import equipment.EquipmentFireClaw;
import equipment.EquipmentGoldBracelet;
import equipment.EquipmentLeatherHat;
import equipment.EquipmentLidOfPod;
import equipment.EquipmentMeteoriteBracelet;
import equipment.EquipmentNomalClothes;
import equipment.EquipmentRubyOfStrength;
import item.ItemElixir;
import item.ItemGreaterHealingPotion;
import item.ItemGreaterMagicPotion;
import item.ItemHealingPotion;
import item.ItemMagicPotion;
import item.ItemMaximApple;
import item.ItemMaximTomato;
import item.ItemMedHerb;
import item.ItemMushroom;
import item.ItemPhoenixTail;
import item.ItemPrayerRing;
import item.ItemStone;
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
			
			member.add(chr);
			chr.party = this;
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				chr.party.pKind = PARTY_KIND_ALLY;
			} else {
				chr.party.pKind = PARTY_KIND_ENEMY;
			}
			
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
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				chr.party.pKind = PARTY_KIND_ALLY;
			} else {
				chr.party.pKind = PARTY_KIND_ENEMY;
			}
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
		int sumOfJobs = 6;
		int jobNo = IO.randomNum(sumOfJobs - 1);// ジョブ数-1を記入
		switch (jobNo) {
		case 0:
			chr = new BraveChr(name);
			break;
		case 1:
			chr = new PriestChr(name);
			break;
		case 2:
			chr = new FighterChr(name);
			break;
		case 3:
			chr = new WarriorChr(name);
			break;
		case 4:
			chr = new DancerChr(name);
			break;
		case 5:
			chr = new LuminaryChr(name);
			break;
		}
		return chr;
	}
	
	private void selectItem(Chr chr) {
		for (int i = 0; i < MAX_ITEM_NUMBER; i++) {
			int sumOfItems = 12;
			int itemNo = IO.randomNum(sumOfItems - 1);
			
			switch (itemNo) {
			case 0:
				chr.items.add(new ItemMedHerb(chr));
				break;
			case 1:
				chr.items.add(new ItemPhoenixTail(chr));
				break;
			case 2:
				chr.items.add(new ItemStone(chr));
				break;
			case 3:
				chr.items.add(new ItemHealingPotion(chr));
				break;
			case 4:
				chr.items.add(new ItemGreaterHealingPotion(chr));
				break;
			case 5:
				chr.items.add(new ItemMaximTomato(chr));
				break;
			case 6:
				chr.items.add(new ItemMagicPotion(chr));
				break;
			case 7:
				chr.items.add(new ItemGreaterMagicPotion(chr));
				break;
			case 8:
				chr.items.add(new ItemMaximApple(chr));
				break;
			case 9:
				chr.items.add(new ItemElixir(chr));
				break;
			case 10:
				chr.items.add(new ItemPrayerRing(chr));
				break;
			case 11:
				chr.items.add(new ItemMushroom(chr));
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
		int accNo = IO.randomNum(3);
		
		switch (accNo) {
		case 0:
			return new EquipmentGoldBracelet(chr);
		case 1:
			return new EquipmentMeteoriteBracelet(chr);
		case 2:
			return new EquipmentAgilityRing(chr);
		case 3:
			return new EquipmentRubyOfStrength(chr);
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
