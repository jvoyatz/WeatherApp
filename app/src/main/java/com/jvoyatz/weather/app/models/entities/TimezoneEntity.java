package com.jvoyatz.weather.app.models.entities;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Objects;

import javax.annotation.Nonnull;


@Entity
public class TimezoneEntity {
	private String offset;
	private String zone;

	public TimezoneEntity() {
	}

	private TimezoneEntity(Builder builder) {
		offset = builder.offset;
		zone = builder.zone;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@NonNull
	@Override
	public String toString() {
		return "TimezoneEntity{" +
				"offset='" + offset + '\'' +
				", zone='" + zone + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimezoneEntity that = (TimezoneEntity) o;

		return TextUtils.equals(offset, that.offset)
				&& TextUtils.equals(zone, that.zone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offset, zone);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String offset;
		private String zone;

		private Builder() { }

		public Builder withOffset(@Nonnull String offset) {
			this.offset = offset;
			return this;
		}

		public Builder withZone(@Nonnull String zone) {
			this.zone = zone;
			return this;
		}

		public TimezoneEntity build() {
			return new TimezoneEntity(this);
		}
	}
}
