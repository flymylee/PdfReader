package kr.ac.ggu.common;

/**
 * 앱의 커스터마이징을 위한 클래스
 */
public class Config {
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Config)) return false;
        final Config other = (Config) o;
        if (!other.canEqual((Object) this)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Config;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }

    public String toString() {
        return "Config()";
    }
}
