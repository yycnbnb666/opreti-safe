package com.ai.safe.agent.terminal;

import com.ai.safe.agent.terminal.CommandExecutionEvent;
import com.ai.safe.agent.terminal.SessionDirectoryEvent;

oneway interface ITerminalCallback {
    void onCommandExecutionUpdate(in CommandExecutionEvent event);
    void onSessionDirectoryChanged(in SessionDirectoryEvent event);
} 