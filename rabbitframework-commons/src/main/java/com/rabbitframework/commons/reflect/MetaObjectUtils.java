package com.rabbitframework.commons.reflect;

import com.rabbitframework.commons.reflect.factory.DefaultObjectFactory;
import com.rabbitframework.commons.reflect.factory.ObjectFactory;

public class MetaObjectUtils {

	public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY);

	private static class NullObject {
	}

	public static MetaObject forObject(Object object) {
		return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY);
	}

	public static ObjectFactory getDefaultObjectFactory() {
		return DEFAULT_OBJECT_FACTORY;
	}

}
