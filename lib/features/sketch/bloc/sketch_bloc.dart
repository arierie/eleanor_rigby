import 'dart:async';

import 'package:eleanor_rigby/features/sketch/data/lyrics_repository_impl.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../data/lyrics_repository.dart';

part 'sketch_event.dart';

part 'sketch_state.dart';

class SketchBloc extends Bloc<SketchEvent, SketchState> {
  LyricsRepository repo = LyricsRepositoryImpl();

  SketchBloc() : super(const SketchInitial('', '', '')) {
    on<UpdateBody>(updateBody);
    on<GenerateLyricsEvent>(generateLyrics);
  }

  FutureOr<void> updateBody(UpdateBody event, Emitter<SketchState> emit) {
    emit(SketchState(state.title, event.body, state.output));
  }

  FutureOr<void> generateLyrics(
      GenerateLyricsEvent event, Emitter<SketchState> emit) async {
    emit(Loading(state.title, state.body, state.output));

    await repo
        .generateLyrics(state.body)
        .onError((error, stackTrace) => emit(Error(state.title, state.body, state.output,
            "There's something wrong. The system failed to generate lyrics.")))
        .then((value) => emit(Success(state.title, state.body, value as String)));
  }
}
