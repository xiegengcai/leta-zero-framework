package cn.leta.zero.filter;

import java.util.Arrays;

/**
 * @author Xie Gengcai
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/9/2.
 */
public class ZeroFilterValue implements Comparable<ZeroFilterValue> {
    /**
     * 顺序
     */
    private int order;
    private int [] cmds;
    private int [] ignoreCmds;
    private AbstractFilter filter;

    public ZeroFilterValue(int order, int[] cmds, int[] ignoreCmds, AbstractFilter filter) {
        this.order = order;
        this.cmds = cmds;
        this.ignoreCmds = ignoreCmds;
        this.filter = filter;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AbstractFilter getFilter() {
        return filter;
    }

    public void setFilter(AbstractFilter filter) {
        this.filter = filter;
    }

    public int[] getCmds() {
        return cmds;
    }

    public void setCmds(int[] cmds) {
        this.cmds = cmds;
    }

    public int[] getIgnoreCmds() {
        return ignoreCmds;
    }

    public void setIgnoreCmds(int[] ignoreCmds) {
        this.ignoreCmds = ignoreCmds;
    }

    @Override
    public int compareTo(ZeroFilterValue another) {
        return Integer.compare(this.order, another.order);
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(this.order);
        if (this.cmds != null) {
            hashCode = 31* Arrays.hashCode(this.cmds);
        }
        if (this.ignoreCmds != null) {
            hashCode = 31* Arrays.hashCode(this.ignoreCmds);
        }
        hashCode = 31 * this.filter.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ZeroFilterValue) {
            ZeroFilterValue other = (ZeroFilterValue) obj;
            return this.filter.equals(other.filter) && this.order == other.getOrder()
                    && (this.cmds !=null &&this.cmds.equals(other.getCmds()))
                    && (this.ignoreCmds!=null&&this.ignoreCmds.equals(other.getIgnoreCmds()));
        }
        return false;
    }
}