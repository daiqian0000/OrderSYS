package com.asion.business.module.area.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_AREA")
public class Area extends Model<Area> {

    private static final long serialVersionUID = 1L;

    /**
     * 地区编码
     */
    private Long id;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 上级地区编码
     */
    private Integer parentId;
    /**
     * 地区等级
     */
    private Integer level;
    /**
     * 经度
     */
    private Double x;
    /**
     * 纬度
     */
    private Double y;
    /**
     * 空间几何数据
     */
    private String shape;

    @TableField(exist=false)
    private Boolean hasChildren;

    @TableField(exist=false)
    private Area parent;

    @TableField(exist=false)
    private List<Area> children;

    public Long getId() {
        return id;
    }

    public Area setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Area setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Area setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public Area setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Double getX() {
        return x;
    }

    public Area setX(Double x) {
        this.x = x;
        return this;
    }

    public Double getY() {
        return y;
    }

    public Area setY(Double y) {
        this.y = y;
        return this;
    }

    public String getShape() {
        return shape;
    }

    public Area setShape(String shape) {
        this.shape = shape;
        return this;
    }

    public static final String ID = "ID";

    public static final String NAME = "NAME";

    public static final String PARENT_ID = "PARENT_ID";

    public static final String LEVEL = "LEVEL";

    public static final String X = "X";

    public static final String Y = "Y";

    public static final String SHAPE = "SHAPE";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Area{" +
        ", id=" + id +
        ", name=" + name +
        ", parentId=" + parentId +
        ", level=" + level +
        ", x=" + x +
        ", y=" + y +
        ", shape=" + shape +
        "}";
    }

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public List<Area> getChildren() {
		return children;
	}

	public void setChildren(List<Area> children) {
		this.children = children;
	}
}
