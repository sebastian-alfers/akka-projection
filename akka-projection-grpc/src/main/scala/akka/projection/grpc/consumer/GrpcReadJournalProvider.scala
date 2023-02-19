/*
 * Copyright (C) 2022 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.projection.grpc.consumer

import akka.actor.ExtendedActorSystem
import akka.persistence.query.ReadJournalProvider
import akka.projection.grpc.internal.ProtoAnySerialization
import com.typesafe.config.Config

/**
 * Note that `GrpcReadJournal`` should be created with the `GrpcReadJournal`` `apply` / `create` factory method
 * and not from configuration via `GrpcReadJournalProvider` when using Protobuf serialization.
 */
final class GrpcReadJournalProvider(system: ExtendedActorSystem, config: Config, cfgPath: String)
    extends ReadJournalProvider {
  override def scaladslReadJournal(): akka.persistence.query.scaladsl.ReadJournal =
    new scaladsl.GrpcReadJournal(system, config, cfgPath)

  override def javadslReadJournal(): akka.persistence.query.javadsl.ReadJournal =
    new javadsl.GrpcReadJournal(
      new scaladsl.GrpcReadJournal(system, config, cfgPath, ProtoAnySerialization.Prefer.Java))
}
