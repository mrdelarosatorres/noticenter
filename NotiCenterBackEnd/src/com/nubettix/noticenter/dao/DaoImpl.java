package com.nubettix.noticenter.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nubettix.noticenter.annotations.Collection;
import com.nubettix.noticenter.annotations.Column;
import com.nubettix.noticenter.utils.MongoUtils;

public abstract class DaoImpl<T> implements Dao<T> {
	protected MongoUtils mongo;
	private Class<T> type;

	public DaoImpl(Class<T> typeClass) {
		this.type = typeClass;
		mongo = MongoUtils.getInstance();
	}

	/**
	 * crea un nuevo documento
	 */
	public T create(T document) {
		Document o = toDocument(document);
		// inserta el documento
		getMongoCollection().insertOne(o);
		return document;
	}

	/**
	 * Filtra un beanC
	 */
	public List<T> read(T document) {
		Document o = toDocument(document);
		FindIterable<Document> iterable = getMongoCollection().find(o);
		CustomBlock block = new CustomBlock();
		iterable.forEach(block);
		return block.getBeans();
	}

	/**
	 * Combierte el bean a un Document
	 * 
	 * @param document
	 * @return
	 */
	protected Document toDocument(T document) {
		Field[] fields = type.getDeclaredFields();
		Document o = new Document();
		for (Field field : fields) {
			Column column = (Column) field.getAnnotation(Column.class);
			try {
				Object value = PropertyUtils.getSimpleProperty(document, field.getName());
				if (value != null) {
					o.append(column.name(), value);
				}

			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return o;
	}

	/**
	 * Convierte un documento a un bean
	 * 
	 * @param document
	 * @return
	 */
	protected T toBean(Document document) {
		Field[] fields = type.getDeclaredFields();
		T bean = null;
		try {
			bean = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Field field : fields) {
			Column column = (Column) field.getAnnotation(Column.class);
			if (document.containsKey(column.name())) {
				try {
					PropertyUtils.setSimpleProperty(bean, column.name(), document.get(column.name()));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return bean;
	}

	/**
	 * Obtiene el collection del bean
	 * 
	 * @return
	 */
	protected MongoCollection<Document> getMongoCollection() {
		MongoClient cnn = mongo.getConnection();
		Collection anCollection = (Collection) type.getAnnotation(Collection.class);
		MongoDatabase db = cnn.getDatabase(anCollection.dataBase());
		MongoCollection<Document> collection = db.getCollection(anCollection.name());
		return collection;
	}

	class CustomBlock implements Block<Document> {

		private List<T> beans = new LinkedList<T>();

		@Override
		public void apply(final Document doc) {
			beans.add(toBean(doc));
		}

		public List<T> getBeans() {
			return beans;
		}

	}
}
