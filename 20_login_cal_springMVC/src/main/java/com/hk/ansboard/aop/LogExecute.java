package com.hk.ansboard.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExecute {
	
	/*
	 * JoinPoint���� �����ϴ� �޼���
	 *    getArgs():�޼����� �ƱԸ�Ʈ�� ��ȯ
	 *    getTarget():��� ��ü�� ��ȯ
	 *    getSignature(): ȣ��Ǵ� �޼��忡 ���� ������ ��� �ִ� ��ü ��ȯ
	 *         --> getName() : �޼����� �̸��� ����
	 *         --> toLongName() : �޼����� Ǯ����(����Ÿ��, �Ķ����Ÿ�� ��� ǥ��)
	 *         --> toShortName() : �޼��带 ����ؼ� ǥ��(�޼����� �̸��� ǥ��)
	 */

	//target �޼��尡 ����Ǳ� �� ����� ��� ����
	public void before(JoinPoint join) {
		//joinpoint���� Ŭ������ ���ؼ� �ƱԸ�Ʈ�� �����Ѵ�.
		//--> before�޼��尡 ��� Ŭ������ �޼��忡�� ������� �𸣱� ������ joinpoint���� ���Ѵ�.
		Logger log = LoggerFactory.getLogger(join.getTarget()+"");
		log.info("before����(info):����" + join.getSignature().getName());
		log.debug("before����(debug):����:"+join.toLongString());
	}
	
	//target�޼��尡 ���� �� ���� ���������� �������� �� ����� ��� ����
	public void afterReturning(JoinPoint join) {
		//joinpoint���� Ŭ������ ���ؼ� �ƱԸ�Ʈ�� �����Ѵ�.
		//--> before�޼��尡 ��� Ŭ������ �޼��忡�� ������� �𸣱� ������ joinpoint���� ���Ѵ�.
		Logger log = LoggerFactory.getLogger(join.getTarget()+"");
		log.info("before����(info):����" + join.getSignature().getName());
		log.debug("before����(debug):����:"+join.toLongString());
		Object[] args=join.getArgs();
		log.debug("�Ķ����:"+Arrays.toString(args));
	}
	
	//target�޼��忡�� ������ �߻����� �� ����� ��� ����
	public void daoError(JoinPoint join) {
		//joinpoint���� Ŭ������ ���ؼ� �ƱԸ�Ʈ�� �����Ѵ�.
		//--> before�޼��尡 ��� Ŭ������ �޼��忡�� ������� �𸣱� ������ joinpoint���� ���Ѵ�.
		Logger log = LoggerFactory.getLogger(join.getTarget()+"");
		log.info("before����(info):����" + join.getSignature().getName());
		log.debug("before����(debug):����:"+join.toLongString());
		log.debug("debug����:�����߻�");
	}
}
