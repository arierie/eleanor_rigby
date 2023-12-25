part of 'sketch_bloc.dart';

class SketchState extends Equatable {
  final String title;
  final String body;
  final String output;
  const SketchState(this.title, this.body, this.output);

  @override
  List<Object> get props => [];
}

class SketchInitial extends SketchState {
  const SketchInitial(super.title, super.body, super.output);

  @override
  List<Object> get props => [];
}

class Loading extends SketchState {
  const Loading(super.title, super.body, super.output);

  @override
  List<Object> get props => [];
}

class Success extends SketchState {
  const Success(super.title, super.body, super.output);

  @override
  List<Object> get props => [output];
}

class Error extends SketchState {
  final String errorMessage;

  const Error(super.title, super.body, super.output, this.errorMessage);

  @override
  List<Object> get props => [errorMessage];
}
