part of 'sketch_bloc.dart';

abstract class SketchEvent extends Equatable {
  const SketchEvent();
}

class GenerateLyricsEvent extends SketchEvent {

  const GenerateLyricsEvent() : super();

  @override
  List<Object> get props => [];
}

class UpdateBody extends SketchEvent {
  final String body;

  const UpdateBody(this.body): super();

  @override
  List<Object?> get props => [body];
}
