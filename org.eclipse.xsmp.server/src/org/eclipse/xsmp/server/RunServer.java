/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.services.LanguageClient;

import com.google.inject.Guice;

public class RunServer
{

  public static void main(String[] args)
    throws InterruptedException, IOException, ExecutionException
  {
    final var injector = Guice.createInjector(new XsmpServerModule());
    final var languageServer = injector.getInstance(XsmpLanguageServer.class);
    final UnaryOperator<MessageConsumer> wrapper = consumer -> message -> consumer.consume(message);

    final var socketAddress = new InetSocketAddress("0.0.0.0", 5007);
    try (var serverSocket = AsynchronousServerSocketChannel.open().bind(socketAddress))
    {
      final var socketChannel = serverSocket.accept().get();
      final var launcher = Launcher.createIoLauncher(languageServer, LanguageClient.class,
              Channels.newInputStream(socketChannel), Channels.newOutputStream(socketChannel),
              Executors.newCachedThreadPool(), wrapper);
      languageServer.connect(launcher.getRemoteProxy());
      final Future< ? > future = launcher.startListening();

      while (!future.isDone())
      {
        Thread.sleep(10_000L);
      }

    }

  }

}