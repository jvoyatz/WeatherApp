package com.jvoyatz.weather.app.models.entities;

import androidx.room.Entity;

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

	@Override
	public String toString() {
		return "TimezoneEntity{" +
				"offset='" + offset + '\'' +
				", zone='" + zone + '\'' +
				'}';
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
