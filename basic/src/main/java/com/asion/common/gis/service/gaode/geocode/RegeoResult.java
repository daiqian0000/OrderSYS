package com.asion.common.gis.service.gaode.geocode;

import java.util.ArrayList;

import com.asion.common.gis.service.gaode.GaodeResult;

/**
 * 逆向地理编码响应结果类
 * 
 * @author chenboyang
 *
 */
public class RegeoResult extends GaodeResult {

	/**
	 * 逆向地理编码信息
	 */
	private Regeocode regeocode;

	/**
	 * 逆向地理编码信息列表
	 */
	private ArrayList<Regeocode> regeocodes;

	/**
	 * 逆向地理编码信息类
	 * 
	 * @author chenboyang
	 *
	 */
	public static class Regeocode {

		/**
		 * 结构化地址信息
		 */
		private String formatted_address;

		/**
		 * 地址元素
		 */
		private AddressComponent addressComponent;

		/**
		 * poi信息列表
		 */
		private ArrayList<Poi> pois;

		/**
		 * 道路信息列表
		 */
		private ArrayList<Road> roads;

		/**
		 * 道路交叉口列表
		 */
		private ArrayList<Roadinter> roadinters;

		/**
		 * aoi信息列表
		 */
		private ArrayList<Aoi> aois;

		/**
		 * 地址元素信息类
		 * 
		 * @author chenboyang
		 *
		 */
		public static class AddressComponent {

			/**
			 * 坐标点所在国家名称
			 */
			private String country;

			/**
			 * 坐标点所在省名称
			 */
			private String province;

			/**
			 * 坐标点所在城市名称
			 */
			private String city;

			/**
			 * 城市编码
			 */
			private String citycode;

			/**
			 * 坐标点所在区
			 */
			private String district;

			/**
			 * 行政区编码
			 */
			private String adcode;

			/**
			 * 坐标点所在乡镇、街道
			 */
			private String township;

			/**
			 * 乡镇街道编码
			 */
			private String towncode;

			/**
			 * 社区信息
			 */
			private Info neighborhood;

			/**
			 * 楼信息
			 */
			private Info building;

			/**
			 * 门牌信息
			 */
			private StreetNumber streetNumber;

			/**
			 * 所属海域
			 */
			private String seaArea;

			/**
			 * 经纬度所属商圈
			 */
			private ArrayList<BusinessArea> businessAreas;

			/**
			 * 信息类
			 * 
			 * @author chenboyang
			 *
			 */
			public static class Info {

				/**
				 * 名称
				 */
				private String name;

				/**
				 * 类型
				 */
				private String type;

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}

			}

			/**
			 * 门牌信息类
			 * 
			 * @author chenboyang
			 *
			 */
			public static class StreetNumber {

				/**
				 * 街道名称
				 */
				private String street;

				/**
				 * 门牌号
				 */
				private String number;

				/**
				 * 坐标点
				 */
				private String location;

				/**
				 * 方向
				 */
				private String direction;

				/**
				 * 距离
				 */
				private double distance;

				public String getStreet() {
					return street;
				}

				public void setStreet(String street) {
					this.street = street;
				}

				public String getNumber() {
					return number;
				}

				public void setNumber(String number) {
					this.number = number;
				}

				public String getLocation() {
					return location;
				}

				public void setLocation(String location) {
					this.location = location;
				}

				public String getDirection() {
					return direction;
				}

				public void setDirection(String direction) {
					this.direction = direction;
				}

				public double getDistance() {
					return distance;
				}

				public void setDistance(double distance) {
					this.distance = distance;
				}

			}

			/**
			 * 所属商圈类
			 * 
			 * @author chenboyang
			 *
			 */
			public static class BusinessArea {

				/**
				 * 商圈信息
				 */
				private String businessArea;

				/**
				 * 商圈中心点经纬度
				 */
				private String location;

				/**
				 * 商圈名称
				 */
				private String name;

				/**
				 * 商圈所在的地区编码
				 */
				private String id;

				public String getBusinessArea() {
					return businessArea;
				}

				public void setBusinessArea(String businessArea) {
					this.businessArea = businessArea;
				}

				public String getLocation() {
					return location;
				}

				public void setLocation(String location) {
					this.location = location;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

			}

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getCitycode() {
				return citycode;
			}

			public void setCitycode(String citycode) {
				this.citycode = citycode;
			}

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

			public String getAdcode() {
				return adcode;
			}

			public void setAdcode(String adcode) {
				this.adcode = adcode;
			}

			public String getTownship() {
				return township;
			}

			public void setTownship(String township) {
				this.township = township;
			}

			public String getTowncode() {
				return towncode;
			}

			public void setTowncode(String towncode) {
				this.towncode = towncode;
			}

			public Info getNeighborhood() {
				return neighborhood;
			}

			public void setNeighborhood(Info neighborhood) {
				this.neighborhood = neighborhood;
			}

			public Info getBuilding() {
				return building;
			}

			public void setBuilding(Info building) {
				this.building = building;
			}

			public StreetNumber getStreetNumber() {
				return streetNumber;
			}

			public void setStreetNumber(StreetNumber streetNumber) {
				this.streetNumber = streetNumber;
			}

			public String getSeaArea() {
				return seaArea;
			}

			public void setSeaArea(String seaArea) {
				this.seaArea = seaArea;
			}

			public ArrayList<BusinessArea> getBusinessAreas() {
				return businessAreas;
			}

			public void setBusinessAreas(ArrayList<BusinessArea> businessAreas) {
				this.businessAreas = businessAreas;
			}

		}

		/**
		 * poi信息类
		 * 
		 * @author chenboyang
		 *
		 */
		public static class Poi {

			/**
			 * poi的id
			 */
			private String id;

			/**
			 * poi点名称
			 */
			private String name;

			/**
			 * poi类型
			 */
			private String type;

			/**
			 * 电话
			 */
			private String tel;

			/**
			 * 方向
			 */
			private String direction;

			/**
			 * 该POI到请求坐标的距离
			 */
			private double distance;

			/**
			 * 坐标点
			 */
			private String location;

			/**
			 * poi地址信息
			 */
			private String address;

			/**
			 * poi信息权重
			 */
			private double poiweight;

			/**
			 * poi所在商圈名称
			 */
			private String businessarea;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public double getDistance() {
				return distance;
			}

			public void setDistance(double distance) {
				this.distance = distance;
			}

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public double getPoiweight() {
				return poiweight;
			}

			public void setPoiweight(double poiweight) {
				this.poiweight = poiweight;
			}

			public String getBusinessarea() {
				return businessarea;
			}

			public void setBusinessarea(String businessarea) {
				this.businessarea = businessarea;
			}

		}

		/**
		 * 道路信息类
		 * 
		 * @author chenboyang
		 *
		 */
		public static class Road {

			/**
			 * 道路id
			 */
			private String id;

			/**
			 * 道路名称
			 */
			private String name;

			/**
			 * 道路到请求坐标的距离
			 */
			private String direction;

			/**
			 * 方位
			 */
			private double distance;

			/**
			 * 坐标点
			 */
			private String location;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public double getDistance() {
				return distance;
			}

			public void setDistance(double distance) {
				this.distance = distance;
			}

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

		}

		/**
		 * 道路交叉口类
		 * 
		 * @author chenboyang
		 *
		 */
		public static class Roadinter {

			/**
			 * 方位
			 */
			private String direction;

			/**
			 * 距离
			 */
			private double distance;

			/**
			 * 路口经纬度
			 */
			private String location;

			/**
			 * 第一条道路id
			 */
			private String first_id;

			/**
			 * 第一条道路名称
			 */
			private String first_name;

			/**
			 * 第二条道路id
			 */
			private String second_id;

			/**
			 * 第二条道路名称
			 */
			private String second_name;

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public double getDistance() {
				return distance;
			}

			public void setDistance(double distance) {
				this.distance = distance;
			}

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public String getFirst_id() {
				return first_id;
			}

			public void setFirst_id(String first_id) {
				this.first_id = first_id;
			}

			public String getFirst_name() {
				return first_name;
			}

			public void setFirst_name(String first_name) {
				this.first_name = first_name;
			}

			public String getSecond_id() {
				return second_id;
			}

			public void setSecond_id(String second_id) {
				this.second_id = second_id;
			}

			public String getSecond_name() {
				return second_name;
			}

			public void setSecond_name(String second_name) {
				this.second_name = second_name;
			}

		}

		/**
		 * aoi信息类
		 * 
		 * @author chenboyang
		 *
		 */
		public static class Aoi {

			/**
			 * 所属aoi的id
			 */
			private String id;

			/**
			 * 所属aoi名称
			 */
			private String name;

			/**
			 * 所属aoi所在区域编码
			 */
			private String adcode;

			/**
			 * 所属aoi中心点坐标
			 */
			private String location;

			/**
			 * 所属aoi点面积
			 */
			private double area;

			/**
			 * 距离
			 */
			private double distance;

			/**
			 * aoi类型
			 */
			private String type;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getAdcode() {
				return adcode;
			}

			public void setAdcode(String adcode) {
				this.adcode = adcode;
			}

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public double getArea() {
				return area;
			}

			public void setArea(double area) {
				this.area = area;
			}

			public double getDistance() {
				return distance;
			}

			public void setDistance(double distance) {
				this.distance = distance;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}

		public AddressComponent getAddressComponent() {
			return addressComponent;
		}

		public void setAddressComponent(AddressComponent addressComponent) {
			this.addressComponent = addressComponent;
		}

		public ArrayList<Poi> getPois() {
			return pois;
		}

		public void setPois(ArrayList<Poi> pois) {
			this.pois = pois;
		}

		public ArrayList<Road> getRoads() {
			return roads;
		}

		public void setRoads(ArrayList<Road> roads) {
			this.roads = roads;
		}

		public ArrayList<Roadinter> getRoadinters() {
			return roadinters;
		}

		public void setRoadinters(ArrayList<Roadinter> roadinters) {
			this.roadinters = roadinters;
		}

		public ArrayList<Aoi> getAois() {
			return aois;
		}

		public void setAois(ArrayList<Aoi> aois) {
			this.aois = aois;
		}

	}

	public Regeocode getRegeocode() {
		return regeocode;
	}

	public void setRegeocode(Regeocode regeocode) {
		this.regeocode = regeocode;
	}

	public ArrayList<Regeocode> getRegeocodes() {
		return regeocodes;
	}

	public void setRegeocodes(ArrayList<Regeocode> regeocodes) {
		this.regeocodes = regeocodes;
	}

}
