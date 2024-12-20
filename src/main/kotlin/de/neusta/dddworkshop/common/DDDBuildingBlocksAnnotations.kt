package de.neusta.dddworkshop.common

import org.springframework.stereotype.Component

/**
 * Marks given class as DDD building block 'aggregate root'.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class AggregateRoot

/**
 * Marks given class as DDD building block 'entity'.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Entity

/**
 * Marks given class as DDD building block 'value object'.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class ValueObject

/**
 * Marks given class as DDD building block 'domain event'.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class DomainEvent

/**
 * Marks given class as DDD building block 'repository'.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Repository

/**
 * Marks class as 'use case'.
 * In DDD this building block is a 'service'.
 * This annotation extends Spring Boot @Component a class marked with this extension will be known in spring context.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class UseCase